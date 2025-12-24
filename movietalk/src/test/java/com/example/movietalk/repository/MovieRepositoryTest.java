package com.example.movietalk.repository;

import java.util.Arrays;
import java.util.List;
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
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.movietalk.member.entity.Member;
import com.example.movietalk.member.entity.constant.Role;
import com.example.movietalk.member.repository.MemberRepository;
import com.example.movietalk.movie.entity.Movie;
import com.example.movietalk.movie.entity.MovieImage;
import com.example.movietalk.movie.entity.Review;
import com.example.movietalk.movie.repository.MovieImageRepository;
import com.example.movietalk.movie.repository.MovieRepository;
import com.example.movietalk.movie.repository.ReviewRepository;

@Disabled
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

    @Test
    @Transactional
    @Commit
    public void deleteByMemberTest() {
        // 회원삭제 => 회원과 리뷰가 외래키 관계이기 때문에 순차적으로 삭제
        // 1번과 2번을 동시에 진행하기 위해 transactional 어노테이션 필요
        // 1. 회원이 작성한 리뷰 제거
        reviewRepository.deleteByMember(Member.builder().mid(2L).build());
        // 2. 회원 삭제
        memberRepository.deleteById(2L);
    }

    // @Transactional(readOnly = true)
    @Test
    public void getMovieReviewTest() {
        List<Review> result = reviewRepository.findByMovie(Movie.builder().mno(100L).build());

        result.forEach(r -> {
            System.out.println(r);

            // 리뷰 작성자 조회
            System.out.println(r.getMember().getEmail());
        });
    }

    // 조회
    // mno, 영화 이미지 중 첫 번째 이미지, 영화제목, 리뷰 수, 리뷰 평균 점수, 영화 등록일
    @Test
    public void moiveListTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);
        for (Object[] objects : result) {
            // System.out.println(Arrays.toString(objects));
            Movie movie = (Movie) objects[0];
            MovieImage movieImage = (MovieImage) objects[1];
            Long reviewCnt = (Long) objects[2];
            Double avgGrade = (Double) objects[3];
            System.out.println(movie);
            System.out.println(movieImage);
            System.out.println(reviewCnt);
            System.out.println(avgGrade);
        }
    }

    @Test
    public void getMovieWithAllTest() {
        List<Object[]> result = movieRepository.getMovieWithAll(100L);
        for (Object[] objects : result) {

            System.out.println(Arrays.toString(objects));
        }
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
