package org.example.dao;

import org.example.model.Owner;
import java.util.List;

public interface OwnerDAO {
    void save(Owner owner);
    Owner findById(int id);
    Owner findByName(String name);
    List<Owner> findAll();
    void update(Owner owner);
    void delete(Owner owner);
}
