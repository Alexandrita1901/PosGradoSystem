package FCA.Sistema.Web.Controller;
import FCA.Sistema.Web.DTO.DashboardResponse;
import FCA.Sistema.Web.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;

    @GetMapping("/options")
    public ResponseEntity<DashboardResponse> getDashboardOptions() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        String role = userRepository.findRoleNameByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails.getUsername()));
        
        List<String> options;
        switch (role) {
            case "SUPERADMIN":
                options = Arrays.asList("Option1", "Option2", "Option3", "Option4", "AdvancedOption");
                break;
            case "ADMIN":
                options = Arrays.asList("Option1", "Option2", "ManageStudents", "ManagePayments");
                break;
            case "USER":
                options = Arrays.asList("ViewDocuments", "ViewSemesters", "ViewPayments");
                break;
            default:
                options = Arrays.asList("AccessDenied");
                break;
        }

        DashboardResponse response = new DashboardResponse(options);
        return ResponseEntity.ok(response);
    }
}
