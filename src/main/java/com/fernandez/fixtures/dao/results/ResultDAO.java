package com.fernandez.fixtures.dao.results;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultDAO {
    public String home;
    public String away;
}
