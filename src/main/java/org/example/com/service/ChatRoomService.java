package org.example.com.service;

import org.example.com.domain.Attendee;
import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;
import org.example.com.domain.Invite;
import org.example.com.dto.Channel;
import org.example.com.dto.InviteCard;
import org.example.com.dto.InviteDTO;
import org.example.com.excep.NoSuchDataException;
import org.example.com.repo.AttendeeRepository;
import org.example.com.repo.ChatRoomRepository;
import org.example.com.repo.EmployeeRepository;
import org.example.com.repo.InviteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final EmployeeRepository employeeRepository;
    private final InviteRepository inviteRepository;
    private final AttendeeRepository attendeeRepository;
    public ChatRoomService(ChatRoomRepository chatRoomRepository, EmployeeRepository employeeRepository, InviteRepository inviteRepository, AttendeeRepository attendeeRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.employeeRepository = employeeRepository;
        this.inviteRepository = inviteRepository;
        this.attendeeRepository = attendeeRepository;
    }



    // 채팅방 생성
    @Transactional
    public ChatRoom createChatRoom(String name, String username){
        Employee employee = employeeRepository.findEmployeeByUsername(username);

        ChatRoom room = ChatRoom.createRoom(name, employee);
        chatRoomRepository.save(room);

        Attendee attendee = Attendee.builder()
                .chatRoom(room)
                .employee(employee)
                .build();
        attendeeRepository.save(attendee);
        return room;
    }



    // 나의 채팅방 조회
    public List<ChatRoom> getMyChatRooms(String username){
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        List<Attendee> attendees = attendeeRepository.findByEmployee(employee)
                .orElseThrow(() -> new NoSuchDataException("해당 사용자의 채팅방이 존재하지 않습니다."));

        List<ChatRoom> chatRoomList = new ArrayList<>();

        attendees.forEach(x -> {
            chatRoomList.add(x.getChatRoom());
        });

        return chatRoomList;
    }

    // 채팅방의 사용자 조회
    public List<Employee> getEmployeeInRoom(String code){
        ChatRoom chatRoom = chatRoomRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchDataException("채팅방이 존재하지 않습니다."));

        List<Attendee> attendees = attendeeRepository.findByChatRoom(chatRoom)
                .orElseThrow(() -> new NoSuchDataException("해당 채팅방에 사용자가 존재하지 않습니다."));

        List<Employee> employees = new ArrayList<>();

        attendees.forEach(x -> {employees.add(x.getEmployee());});

        return employees;
    }

    // 채팅 방삭제
    @Transactional
    public void deleteChatRoom(String code){
        ChatRoom chatRoom = chatRoomRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchDataException("해당데이터가 존재하지 않습니다."));
        chatRoomRepository.delete(chatRoom);

    }

    // 채팅방 초대
    public InviteCard inviteEmployee(InviteDTO inviteDTO){
        Employee fromUser = employeeRepository.findEmployeeByUsername(inviteDTO.getFrom());
        Employee toUser = employeeRepository.findEmployeeByUsername(inviteDTO.getTo());
        ChatRoom chatRoom = chatRoomRepository.findByCode(inviteDTO.getCode())
                .orElseThrow(() -> new NoSuchDataException("해당데이터가 존재하지 않습니다."));

        if(!fromUser.getUsername().equals(chatRoom.getCreator().getUsername())){
            return null;
        }
        Invite invite = Invite.builder()
                .code(inviteDTO.getCode())
                .from(fromUser)
                .to(toUser)
                .build();
        inviteRepository.save(invite);
        return new InviteCard(fromUser, chatRoom);
    }
    

    // 초대 수락
    @Transactional
    public void acceptInvite(Channel channel){
        Employee me = employeeRepository.findEmployeeByUsername(channel.getUsername());
        ChatRoom chatRoom = chatRoomRepository.findByCode(channel.getCode())
                .orElseThrow(() -> new NoSuchDataException("해당데이터가 존재하지 않습니다."));

        Attendee attendee = Attendee.builder()
                .chatRoom(chatRoom)
                .employee(me)
                .build();

        attendeeRepository.save(attendee);
        inviteRepository.deleteByCode(channel.getCode());
    }
    // 초대 거절
    public void rejectInvite(Channel channel){
        Employee me = employeeRepository.findEmployeeByUsername(channel.getUsername());
        inviteRepository.deleteByCode(channel.getCode());
    }

    // 초대장 목록
    public List<InviteCard> getInviteList(Channel channel){
        Employee employee = employeeRepository.findEmployeeByUsername(channel.getUsername());
        List<Invite> invites = inviteRepository.findInvitesByTo(employee)
                .orElseThrow(() -> new NoSuchDataException("해당데이터가 존재하지 않습니다."));

        List<InviteCard> list = new ArrayList<>();

        invites.forEach(e -> {
            ChatRoom chatRoom = chatRoomRepository.findByCode(e.getCode())
                    .orElseThrow(() -> new NoSuchDataException("해당데이터가 존재하지 않습니다."));

            list.add(new InviteCard(e.getFrom(), chatRoom));
        });
        return list;
    }
    // 채팅방 퇴장
    @Transactional
    public void exitChannel(String code, String username){
        ChatRoom chatRoom = chatRoomRepository
                .findByCode(code).orElseThrow(() -> (new IllegalArgumentException("채널이 존재하지 않습니다.")));

        // 제거할 유저
        Employee employee = employeeRepository.findEmployeeByUsername(username);

        attendeeRepository.deleteByChatRoomAndEmployee(chatRoom, employee);
    }
    // 방 삭제
    public void deleteRoom(ChatRoom chatRoom){
        chatRoomRepository.delete(chatRoom);
    }
}
