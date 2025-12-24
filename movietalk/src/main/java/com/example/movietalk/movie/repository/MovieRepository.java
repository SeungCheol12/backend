package com.example.movietalk.movie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.movietalk.movie.entity.Movie;

// + 를 사용해 여러 줄로 할 때는 반드시 마지막에 띄어쓴다
public interface MovieRepository extends JpaRepository<Movie, Long> {
        @Query(value = "select m, mi, count(distinct r), avg(coalesce(r.grade, 0)) "
                        + "from Movie m left outer join MovieImage mi on mi.movie = m and mi.ord = 0 "
                        + "left join Review r on r.movie = m group by m, mi", countQuery = "select count(m) from Movie m")
        Page<Object[]> getListPage(Pageable pageable);

        // 영화 상세 조회
        @Query("select m, mi, count(distinct r), avg(coalesce(r.grade, 0)) from Movie m "
                        + "left outer join MovieImage mi on mi.movie = m "
                        + " left join Review r on r.movie = m where m.mno = :mno group by mi")
        List<Object[]> getMovieWithAll(@Param("mno") Long mno);
}
