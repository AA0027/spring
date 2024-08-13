package org.example.com.config;

import org.example.com.domain.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private Employee employee;

    public Employee getUser() { return this.employee; }

    public PrincipalDetails(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        if(employee.getRole() == null) return collection;

        Arrays.stream(employee.getRole().split(","))
                .forEach(auth -> collection.add(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return auth.trim();
                    }

                    @Override
                    public String toString() {
                        return auth.trim();
                    }
                }));
        return collection;
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getUsername();
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
}
