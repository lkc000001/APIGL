package com.ApiGL.entity.response;

import java.util.List;

import lombok.Data;

public @Data class JSGridReturnData<T> {
	List<T> data;          // array of items
	long itemsCount;    // total items amount in storage
};
