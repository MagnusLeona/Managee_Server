package com.magnus.project.managee.work.mapper.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination<T> implements Serializable {
    public List<T> results;
    public int total;
}
