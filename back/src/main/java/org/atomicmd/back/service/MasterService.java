package org.atomicmd.back.service;

import org.atomicmd.back.model.dto.*;

import java.util.List;
import java.util.UUID;

public interface MasterService {
    MasterPostResponseDto getMasterById(UUID masterId);

    List<MasterPostResponseDto> getMasterAll();

    MasterPostResponseDto postMaster(MasterPostDto masterPostDto);

    MasterPutResponseDto putMaster(UUID masterId, MasterPutDto masterPutDto);

    DetailResponseDto postDetail(UUID masterId, DetailPostDto detailPostDto);

    DetailResponseDto putDetail(UUID masterId, UUID detailId, DetailPostDto detailPostDto);

    boolean deleteMaster(UUID masterId);

    boolean deleteDetail(UUID masterId, UUID detailId);
}
