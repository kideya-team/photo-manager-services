package com.kideya.photomanagerbot.botapi.bot_workers.simple_flow;

import com.kideya.photomanagerbot.botapi.bot_workers.simple_flow.handlers.Handler;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Params {

    private String resourceTag;
    private String argName;
    private String restPoint;

    private String arg;
}
