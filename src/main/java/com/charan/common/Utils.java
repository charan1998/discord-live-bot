package com.charan.common;

import com.charan.exceptions.MultipleMentionsException;
import com.charan.exceptions.MultipleUsersException;
import com.charan.exceptions.UsernameNotFoundException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

public class Utils {
    public static String extractStreamerId(Guild guild, Message message) throws MultipleMentionsException,
            UsernameNotFoundException, MultipleUsersException {

        if (message.getMentionedUsers().size() > 0) {
            List<Member> mentionedMembers = message.getMentionedMembers();

            if (mentionedMembers.size() > 1) {
                throw new MultipleMentionsException();
            }

            return mentionedMembers.get(0).getId();
        }
        else {
            String command = message.getContentRaw();
            String username = command.substring(Constants.NOTIFY_COMMAND.length() + 1);

            List<Member> matchedMembers = guild.getMembersByNickname(username, true);
            matchedMembers = matchedMembers.size() == 0 ? guild.getMembersByName(username, true) : matchedMembers;

            if (matchedMembers.size() == 0) {
                throw new UsernameNotFoundException();
            }
            else if (matchedMembers.size() > 1) {
                throw new MultipleUsersException();
            }

            return matchedMembers.get(0).getId();
        }
    }
    
    public static String getUsername(Member member) {
        return member.getNickname() == null ? member.getUser().getName() : member.getNickname();
    }
}
