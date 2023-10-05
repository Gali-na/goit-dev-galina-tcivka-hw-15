package com.example.security;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class СustomUserDetailsService implements UserDetailsService {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDate userDate=getPaswordByName( username);
        if(userDate==null){
            throw new UsernameNotFoundException(username);
        }
     UserDetails userDetails=new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
               return userDate.getPassword();
            }
            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getPassword());

        System.out.println(userDetails);
        return userDetails;
    }

    private UserDate getPaswordByName(String username) {
        String sql="SELECT password_customer FROM customer WHERE login=:username";
        return jdbcTemplate.queryForObject(sql,Map.of("username",username),new UserDateMapper());

    }

    private class UserDateMapper implements RowMapper <UserDate> {

        @Override
        public UserDate mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserDate(rs.getString("password_customer"));
        }
    }
    @Data
    @Builder
    private static class UserDate {
        private String password;
    }
}
