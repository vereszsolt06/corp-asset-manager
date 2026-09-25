package hu.unideb.inf.corp.web;

import hu.unideb.inf.corp.model.Asset;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AssetController {
    @RequestMapping(path = "/api/asset/{serialNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Asset getOne(@PathVariable String serialNumber);

    @GetMapping("/api/asset")
    List<Asset> getAll();

    @PostMapping("/api/asset")
    Asset createOne(@RequestBody Asset asset);

    @PutMapping("/api/asset")
    Asset updateOne(@RequestBody Asset asset);

    @DeleteMapping("/api/asset/{serialNumber}")
    void deleteOne(@PathVariable String serialNumber);
}
