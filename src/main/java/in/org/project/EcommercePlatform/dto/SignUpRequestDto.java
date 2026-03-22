package in.org.project.EcommercePlatform.dto;

import in.org.project.EcommercePlatform.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String userName;
    private String passWord;
    private List<RoleType>roles=new ArrayList<>();
}