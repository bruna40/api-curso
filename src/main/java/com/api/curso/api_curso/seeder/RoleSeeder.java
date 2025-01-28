// package com.api.curso.api_curso.seeder;

// import org.springframework.stereotype.Component;
// import com.api.curso.api_curso.module.Roles.enums.RoleEnum;
// import com.api.curso.api_curso.module.Roles.model.entity.Role;
// import com.api.curso.api_curso.module.Roles.repository.RoleRepository;
// import jakarta.annotation.PostConstruct;

// @Component
// public class RoleSeeder {
//      private final RoleRepository roleRepository;

//     public RoleSeeder(RoleRepository roleRepository) {
//         this.roleRepository = roleRepository;
//     }

//     @PostConstruct
//     public void seedRoles() {
//         for (RoleEnum roleEnum : RoleEnum.values()) {
//             if (!roleRepository.findByName(roleEnum.name()).isPresent()) {
//                 roleRepository.save(new Role(roleEnum.name()));
//             }
//         }
//     }
// }
