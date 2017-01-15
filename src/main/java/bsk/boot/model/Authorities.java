package bsk.boot.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Mateusz-PC on 26.04.2016.
 */
public class Authorities implements GrantedAuthority {
    private String label;
    public Authorities(Integer label) {
        this.label = label.toString();
    }

    @Override
    public String getAuthority() {
        return label;
    }
}
