package com.chemicalguysMall.entity;

import com.chemicalguysMall.constant.MemberRole;
import com.chemicalguysMall.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name="member")
@Getter @Setter @ToString
public class Member extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setAddress(memberDto.getAddress());
        String password = passwordEncoder.encode(memberDto.getPassword1());
        member.setPassword(password);
        member.setMemberRole(MemberRole.USER);
        return member;
    }

    public void updateMember(MemberDto memberDto, PasswordEncoder passwordEncoder){
        this.name = memberDto.getName();
        this.address = memberDto.getAddress();
        String password = passwordEncoder.encode(memberDto.getPassword1());
        this.password = password;
    }
}
