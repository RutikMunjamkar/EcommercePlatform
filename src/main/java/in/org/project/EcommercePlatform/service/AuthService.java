package in.org.project.EcommercePlatform.service;

import in.org.project.EcommercePlatform.dto.LoginRequestDto;
import in.org.project.EcommercePlatform.dto.LoginResponseDto;
import in.org.project.EcommercePlatform.dto.SignUpRequestDto;
import in.org.project.EcommercePlatform.dto.SignUpResponseDto;
import in.org.project.EcommercePlatform.entity.Customer;
import in.org.project.EcommercePlatform.entity.Merchant;
import in.org.project.EcommercePlatform.entity.User;
import in.org.project.EcommercePlatform.exception.CustomException;
import in.org.project.EcommercePlatform.repository.CustomerRepository;
import in.org.project.EcommercePlatform.repository.MerchantRepository;
import in.org.project.EcommercePlatform.repository.UserRepository;
import in.org.project.EcommercePlatform.type.MerchantStatus;
import in.org.project.EcommercePlatform.type.RoleType;
import in.org.project.EcommercePlatform.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    MerchantRepository merchantRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthUtil authUtil;

    @Transactional
    public SignUpResponseDto signUpUser(SignUpRequestDto signUpRequestDto){
        User existinUser=userRepository.findByUserName(signUpRequestDto.getUserName());
        if(existinUser!=null){
            throw new CustomException("The User is already Present",500);
        }
        User user=User.builder().userName(signUpRequestDto.getUserName()).password(passwordEncoder.encode(signUpRequestDto.getPassWord()))
                .roles(signUpRequestDto.getRoles()).build();
        List<RoleType> roles=user.getRoles();
        if(roles.stream().anyMatch(role->role.toString().equalsIgnoreCase(RoleType.CUSTOMER.toString()))){
            Customer customer=Customer.builder().user(user).build();
            customerRepository.save(customer);
        }
        else if(roles.stream().anyMatch(role->role.toString().equalsIgnoreCase(RoleType.CUSTOMER.toString()))){
            Merchant merchant=Merchant.builder().user(user).status(MerchantStatus.PENDING).build();
            merchantRepository.save(merchant);
        }
        else if(roles.stream().anyMatch(role->role.toString().equalsIgnoreCase(RoleType.ADMIN.toString()))){
            userRepository.save(user);
        }
        else{
            throw new CustomException("The User does not have the valid role",500);
        }
        return new SignUpResponseDto(user.getId(),user.getUsername());
    }

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto){
        User exitingUser=userRepository.findByUserName(loginRequestDto.getUserName());
        if(exitingUser==null){
            throw new CustomException("The User is not present",500);
        }
        String token= authUtil.generateAccessToken(exitingUser);
        Authentication authenticate=  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(),loginRequestDto.getPassWord()));
        User user= (User) authenticate.getPrincipal();
        return new LoginResponseDto(user.getId(), token);
    }

    @Transactional
    public LoginResponseDto updatePassWord(String newPassWord){
        String token= SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        String userName=authUtil.getUserName(token.substring(7));
        User user=userRepository.findByUserName(userName);
        user.setPassword(passwordEncoder.encode(newPassWord));
        userRepository.save(user);
        return new LoginResponseDto(user.getId(),token);
    }
}
