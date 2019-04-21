package com.denis.pavlovich.weatherapp.data.database.repository;

import android.support.annotation.NonNull;

public interface IRepository<T> {
  void delete(@NonNull T obj);
  void add(@NonNull T obj);
  void edit(@NonNull T obj);
}
