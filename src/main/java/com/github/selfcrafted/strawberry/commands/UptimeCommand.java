package com.github.selfcrafted.strawberry.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;

public class UptimeCommand extends Command {
    UptimeCommand() {
        super("uptime");
        addSyntax((sender, context) -> {
            RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
            var duration = Duration.ofMillis(rb.getUptime());
            var days = duration.toDays();
            var hours = duration.toHoursPart();
            var minutes = duration.toMinutesPart();
            var seconds = duration.toSecondsPart();
            var message = Component.text(
                    String.format("Up %d days %02d:%02d:%02d", days, hours, minutes, seconds));
            sender.sendMessage(message);
        });
    }
}
