package webserver2.newproject.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import webserver2.newproject.auth.filter.JwtAuthenticationFilter;
import webserver2.newproject.auth.filter.JwtVerificationFilter;
import webserver2.newproject.auth.handler.MemberAccessDeniedHandler;
import webserver2.newproject.auth.handler.MemberAuthenticationEntryPoint;
import webserver2.newproject.auth.handler.MemberAuthenticationFailureHandler;
import webserver2.newproject.auth.handler.MemberAuthenticationSuccessHandler;
import webserver2.newproject.auth.jwt.JwtTokenizer;
import webserver2.newproject.auth.utils.CustomAuthorityUtils;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration //설정파일 만드는 거다.
@EnableWebSecurity(debug = true) //Spring security 라고 선언
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer; // jwt 생성 검증 할때 사용할꺼임
    private final CustomAuthorityUtils authorityUtils; // 권한 처리용으로 가져옴 해당 클래스 보는게 도움될꺼임

    public SecurityConfiguration(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http //아래는 필터 처리인데 내가 원하는 동작을 집어 넣는거다.
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable() // 프론트엔드에서 리엑트로 같이 협업할때는 활성화 해야됨(위조체크임)
                .cors(withDefaults()) //동일출처라고 해킹방지임. 같은 브라우저에서 요청이 왔는지 체크하는것임.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //이건 세션 활성화 하는건데 나는 JWT를 사용할꺼라 STATELESS로 설정했음(그럼 jwt 사용함)
                .and()
                .formLogin().disable() //스프링Security는 기본 로그인 폼(프론트엔드)이 있는데 H2쓸때 걸리적 거려서 disable 했음.
                .httpBasic().disable()  //http 인증 방식인데 나는 jwt인증 방식을 쓸거라서 비활성화 했다.
                .exceptionHandling()//인가 할때 아래 필터들 쓸꺼다.
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                //위에는 성공하면 위의 MemberAuthenticationEntryPoint() 호출할꺼고
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                //실패하면 MemberAccessDeniedHandler() 호출할꺼임.
                .and()
                .apply(new CustomFilterConfigurer())
                //맨 밑에있는 CustomFilterConfigurer() 불러올때 쓸꺼다.
                .and()
                .authorizeHttpRequests(authorize -> authorize //여기는 인가 하는 곳인데 hasRole이 역할인데 나는
                        //회원가입한 사람을 MEMBER라고 역할 부여 했다. 다 중복이라 몇개만 설명하면
                        .antMatchers(HttpMethod.DELETE, "/**").hasRole("MEMBER") //모든 DELETE는 MEMBER만
                        .antMatchers(HttpMethod.PATCH, "/**").hasRole("MEMBER") //마찬가지
                        .antMatchers("/api/members/signup").permitAll() //누구나 해당 api 접속 가능
                        .antMatchers("/api/members/login").permitAll()
                        .antMatchers("/members/**").hasRole("MEMBER")
                        .antMatchers("/api/boards/post").hasRole("MEMBER")
                        .antMatchers(HttpMethod.GET, "/boards/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/replies/**").hasRole("MEMBER")
                        .antMatchers(HttpMethod.GET, "/api/replies/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/likes/**").hasRole("MEMBER")
                        .anyRequest().permitAll()


                );
        return http.build();
    }

    @Bean // 이걸 넣어줘야 비밀번호를 암호화 해줌
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean //아까 위에서 언급한 CORS 설정하는 부분이다.
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration(); //CORS는 configuration 여기에 담길꺼다.
        configuration.setAllowedOrigins(Arrays.asList("*")); //모든 도메인에서 요청 가능하고
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE")); //요청 방식은 4가지로 정의하고
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();//이걸 source 에 담는다.
        source.registerCorsConfiguration("/**", configuration); //어플의 모든곳에 CORS가 적용될꺼다.
        return source; //이걸 다시 configuration에 담을꺼다.
    }


    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        //여기는 스프링 security에서 내 어플 요구에 따라 커스텀을 해서 사용할려고 만들었고 HttpSecurity의 기본설정을 조작할꺼다.
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            //위에는 인증관리자임 가져오고
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            //JWT 토큰을 생성하고 반환하는 역할임
            jwtAuthenticationFilter.setFilterProcessesUrl("/members/login");
            //필터가 "/members/login" 에서 작동하게 할꺼임.
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            //인증 성공시 작동
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());
            //인증 실패시 작동

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);
            //아래 까지 JwtVerificationFilter를 JwtAuthenticationFilter 다음에 실행되도록 하는거임 인증 - 인가 순서니깐


            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}

