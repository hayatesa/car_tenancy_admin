package com.dev.main.tenancy.dao;

import java.util.List;

public interface BaseMapper<T> {

    List<T> query();

}
