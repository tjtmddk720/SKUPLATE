package com.skuplate.server.member.entity;

import com.skuplate.server.bookmark.entity.Bookmark;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    private String name;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private accountProvider provider;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    public enum accountProvider {
        GOOGLE("구글"),
        KAKAO("카카오");
        private String accountProvider;
        accountProvider(String accountProvider){
            this.accountProvider = accountProvider;
        }
        public String getAccountProvider(){
            return accountProvider;
        }
    }
    public void updateEmail(String email) {
        this.email = email;
    }
    public void updateRoles(List<String> roles) {
        this.roles = roles;
    }
    public void updateProvider(accountProvider provider) {
        this.provider=provider;
    }
    public void updateName(String name) {
        this.name = name;
    }
}
