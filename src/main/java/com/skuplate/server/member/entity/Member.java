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
    private Long memberId;
    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    private String name;

    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private accountProvider provider;

    public enum accountProvider {
        google("구글"),
        CHINA("카카오");
        private String accountProvider;
        accountProvider(String accountProvider){
            this.accountProvider = accountProvider;
        }
        public String getAccountProvider(){
            return accountProvider;
        }
    }
}
