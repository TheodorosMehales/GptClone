package com.chatapp.gptclone.controllers;

import com.chatapp.gptclone.exceptions.CloneException;
import com.chatapp.gptclone.model.Model;
import com.chatapp.gptclone.services.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class ModelsController {

    @Autowired
    private ModelsService modelsService;

    @GetMapping
    public ResponseEntity<List<Model>> getAllModels() throws CloneException {
        return ResponseEntity.ok(modelsService.findAllModels());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Model> getModelByName(@PathVariable String name) throws CloneException {
        return ResponseEntity.ok(modelsService.findModelByName(name));
    }

    @PostMapping
    public ResponseEntity<Model> createModel(@RequestBody Model model) throws CloneException {
        return new ResponseEntity<>(modelsService.createModel(model), HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Model> updateModel(@PathVariable String name, @RequestBody Model model) throws CloneException {
        return ResponseEntity.ok(modelsService.updateModel(name, model));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteModel(@PathVariable String name) throws CloneException {
        modelsService.deleteModel(name);
        return ResponseEntity.noContent().build();
    }
}