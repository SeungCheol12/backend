package com.example.movietalk.repository;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.movietalk.member.entity.Member;
import com.example.movietalk.member.entity.constant.Role;
import com.example.movietalk.member.repository.MemberRepository;
import com.example.movietalk.movie.entity.Movie;
import com.example.movietalk.movie.entity.MovieImage;
import com.example.movietalk.movie.entity.Review;
import com.example.movietalk.movie.repository.MovieImageRepository;
import com.example.movietalk.movie.repository.MovieRepository;
import com.example.movietalk.movie.repository.ReviewRepository;

// @Disabled
@SpringBootTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 조회
    // mno, 영화 이미지 중 첫 번째 이미지, 영화제목, 리뷰 수, 리뷰 평균 점수, 영화 등록일
    @Test
    public void moiveListTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void getMovieWithAllTest() {
        Object[] result = movieRepository.getMovieWithAll(100L);
        System.out.println(Arrays.toString(result));
    }

    // 멤버 삽입
    @Test
    public void memeberInsertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gamil.com")
                    .nickname("user" + i)
                    .password(passwordEncoder.encode("1111"))
                    .role(Role.MAMBER)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void reviewInsertTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            // 영화번호 임의 추출
            long mno = (int) (Math.random() * 100) + 1;

            // 리뷰 사용자 임의 추출
            long mid = (int) (Math.random() * 10) + 1;
            Review review = Review.builder()
                    .member(Member.builder().mid(mid).build())
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int) (Math.random() * 5) + 1)
                    .text("review" + i)
                    .build();
            reviewRepository.save(review);

        });
    }

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("Movie Title " + i)
                    .build();
            movieRepository.save(movie);

            // 임의의 이미지 삽입
            int count = (int) (Math.random() * 5) + 1;
            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        // java.util.UUTD
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .ord(j)
                        .imgName("test" + j + ".jpg")
                        .build();
                movieImageRepository.save(movieImage);
            }
        });
    }

}
