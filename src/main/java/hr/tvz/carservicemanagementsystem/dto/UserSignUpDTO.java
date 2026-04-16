package hr.tvz.carservicemanagementsystem.dto;

public record UserSignUpDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String confirmPassword) {
}
