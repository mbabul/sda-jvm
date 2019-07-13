package io.mbab.sda.jvm;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/")
class Controller {

    private DataRepository repo;

    public Controller() {
        this.repo = DataRepository.getInstance();
    }

    @GetMapping
    HttpEntity<String> root() {
        return ResponseEntity.ok(
                "Operations:<br/>" +
                        "<br/><button onClick=\"location.href='/list'\"><b>LIST_ALL<b></button><br/>" +
                        "<br/><button onClick=\"location.href='/generate?size=' + document.getElementById('size').value\"><b>GENERATE<b></button>&nbsp;&nbsp;" +
                            "<input type=\"number\" id=\"size\" value=\"0\" min=\"0\"><br/>" +
                        "<br/><button onClick=\"location.href='/delete_all'\"><b>DELETE_ALL<b></button><br/>" +
                        "<br/><button onClick=\"location.href='/size'\"><b>SIZE<b></button>"
        );
    }

    @GetMapping("/list")
    HttpEntity<List<DataModel>> getAll() {
        return ResponseEntity.ok(repo.getAll());
    }

    @GetMapping("/list/{id}")
    HttpEntity<DataModel> get(@PathVariable int id) {
        return repo.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/generate")
    HttpEntity<String> generate(@RequestParam int size) {
        int actualSize = repo.getAll().size();
        IntStream.range(0, size)
                .mapToObj(e -> new DataModel((e + actualSize), "x"))
                .forEach(e -> repo.save(e));

        return ResponseEntity.ok("Generated " + size + " DataModel objects");
    }

    @GetMapping("/delete_all")
    HttpEntity<String> deleteAll() {
        int size = repo.getAll().size();
        repo.deleteAll();
        return ResponseEntity.ok("Deleted all DataModel objects: " + size);
    }

    @GetMapping("/size")
    HttpEntity<String> size(){
        return ResponseEntity.ok("Actual size: " + repo.getAll().size());
    }
}
