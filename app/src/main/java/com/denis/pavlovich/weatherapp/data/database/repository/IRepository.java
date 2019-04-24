package com.denis.pavlovich.weatherapp.data.database.repository;

import android.support.annotation.NonNull;

import java.util.List;

public interface IRepository<T> {

  void delete(@NonNull T obj);

  void add(@NonNull T obj);

  void edit(@NonNull T obj);

  List<T> getAllList();

  T getById(Long id);
}
