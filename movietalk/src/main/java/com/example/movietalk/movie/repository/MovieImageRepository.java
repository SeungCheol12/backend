package com.example.movietalk.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movietalk.movie.entity.MovieImage;
import com.example.movietalk.movie.entity.Movie;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
    // Movie 를 기준으로 MovieImage 삭제하기
    @Modifying
    @Query("delete from MovieImage mi where mi.movie = :movie")
    void deleteByMovie(Movie movie);

}
