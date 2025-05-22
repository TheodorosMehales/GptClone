package com.chatapp.gptclone.services;

import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.Model;
import com.chatapp.gptclone.repositories.ModelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelsService {

    @Autowired
    private ModelsRepository modelRepo;

    public List<Model> findAllModels() throws CloneException {
        try {
            return modelRepo.findAll();
        } catch (Exception e) {
            throw new CloneException("Error fetching models", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Model findModelByName(String name) throws CloneException {
        try {
            return modelRepo.findById(name)
                    .orElseThrow(() -> new CloneException("Model not found: " + name, HttpStatus.NOT_FOUND));
        } catch (CloneException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CloneException("Error fetching model", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Model createModel(Model model) throws CloneException {
        try {
            return modelRepo.save(model);
        } catch (Exception e) {
            throw new CloneException("Error creating model", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Model updateModel(String name, Model model) throws CloneException {
        try {
            Model existing = findModelByName(name);
            existing.setUrl(model.getUrl());
            return modelRepo.save(existing);
        } catch (CloneException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CloneException("Error updating model", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public void deleteModel(String name) throws CloneException {
        Model existing = findModelByName(name);
        try {
            modelRepo.delete(existing);
        } catch (Exception e) {
            throw new CloneException("Error deleting model", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
