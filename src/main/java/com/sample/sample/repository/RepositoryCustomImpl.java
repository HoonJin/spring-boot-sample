package com.sample.sample.repository;

import com.sample.sample.entity.BaseEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class RepositoryCustomImpl implements RepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public void lockAndRefresh(BaseEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.refresh(entity);
    }
}
