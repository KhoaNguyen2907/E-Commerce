package com.ckt.ecommercecybersoft.address.service;

import com.ckt.ecommercecybersoft.address.dto.AddressDTO;
import com.ckt.ecommercecybersoft.address.model.AddressEntity;
import com.ckt.ecommercecybersoft.address.repository.AddressRepository;
import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface AddressService extends
        GenericService<AddressEntity, AddressDTO, UUID> {

}

@Service
class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ProjectMapper mapper;

    AddressServiceImpl(AddressRepository addressRepository, ProjectMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<AddressEntity, UUID> getRepository() {
        return addressRepository;
    }

    @Override
    public ProjectMapper getMapper() {
        return mapper;
    }
}
