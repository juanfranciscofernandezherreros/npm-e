package com.fernandez.fixtures.dao.results;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;

@Data
@Accessors(chain = true)
public class TeamDAO {
    public String name;
    public byte[] image;
}
