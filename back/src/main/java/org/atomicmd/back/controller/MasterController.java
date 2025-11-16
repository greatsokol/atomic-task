package org.atomicmd.back.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.atomicmd.back.model.dto.*;
import org.atomicmd.back.service.MasterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/master")
public class MasterController {
    private final MasterService masterService;

    @GetMapping
    public List<MasterPostResponseDto> getAllMasters() {
        return masterService.getMasterAll();
    }

    @GetMapping("{masterId}")
    public MasterPostResponseDto getMasterById(@PathVariable("masterId") UUID masterId) {
        MasterPostResponseDto responseDto = masterService.getMasterById(masterId);
        if (responseDto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return responseDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MasterPostResponseDto postMaster(@RequestBody @Valid MasterPostDto postMasterDto) {
        return masterService.postMaster(postMasterDto);
    }

    @PutMapping("{masterId}")
    @ResponseStatus(HttpStatus.OK)
    public MasterPutResponseDto putMaster(@RequestBody @Valid MasterPutDto masterPutDto,
                                       @PathVariable("masterId") UUID masterId) {
        MasterPutResponseDto responseDto = masterService.putMaster(masterId, masterPutDto);
        if (responseDto != null) {
            return responseDto;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{masterId}")
    public void deleteMaster(@PathVariable("masterId") UUID masterId) {
        if (!masterService.deleteMaster(masterId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{masterId}/detail")
    @ResponseStatus(HttpStatus.CREATED)
    public DetailResponseDto postDetail(@PathVariable("masterId") UUID masterId,
                                        @RequestBody @Valid DetailPostDto detailPostDto) {
        DetailResponseDto responseDto = masterService.postDetail(masterId, detailPostDto);
        if (responseDto != null) return responseDto;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{masterId}/detail/{detailId}")
    public DetailResponseDto putDetail(@PathVariable("masterId") UUID masterId,
                                       @PathVariable("detailId") UUID detailId,
                                       @RequestBody DetailPostDto detailPostDto) {
        DetailResponseDto responseDto = masterService.putDetail(masterId, detailId, detailPostDto);
        if (responseDto != null) return responseDto;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{masterId}/detail/{detailId}")
    public void deleteDetail(@PathVariable UUID masterId, @PathVariable UUID detailId) {
        if (!masterService.deleteDetail(masterId, detailId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
