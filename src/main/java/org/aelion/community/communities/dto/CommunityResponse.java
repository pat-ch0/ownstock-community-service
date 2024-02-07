package org.aelion.community.communities.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
public class CommunityResponse {
    @Id
    private String id;
    private String name;
    private City city;
}
