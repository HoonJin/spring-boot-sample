package com.sample.sample.repository;

import com.sample.sample.entity.BaseEntity;

public interface RepositoryCustom {

    void lock(BaseEntity entity);
    
}
