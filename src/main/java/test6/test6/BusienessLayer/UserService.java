package test6.test6.BusienessLayer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import test6.test6.PersistenceLayer.UserRepository;

@Service
@Data
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired private final UserRepository userRepository;


    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(3);
    }

    public User toUserEntity(UserDto userDto) {
        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), "authenticated");
        return user;

    }


    public void saveUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            userRepository.save(toUserEntity(userDto));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found: " + email);
        }

        return new UserDetailsImpl(user);
    }
}
