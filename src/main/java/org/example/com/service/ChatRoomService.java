package org.example.com.service;

import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;
import org.example.com.domain.FindRoom;
import org.example.com.domain.Invite;
import org.example.com.dto.Channel;
import org.example.com.dto.InviteCard;
import org.example.com.dto.InviteDTO;
import org.example.com.excep.NoSuchDataException;
import org.example.com.repo.ChatRoomRepository;
import org.example.com.repo.EmployeeRepository;
import org.example.com.repo.FindRoomRepository;
import org.example.com.repo.InviteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final EmployeeRepository employeeRepository;
    private final FindRoomRepository findRoomRepository;
    private final InviteRepository inviteRepository;
    public ChatRoomService(ChatRoomRepository chatRoomRepository, EmployeeRepository employeeRepository, FindRoomRepository findRoomRepository, InviteRepository inviteRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.employeeRepository = employeeRepository;
        this.findRoomRepository = findRoomRepository;
        this.inviteRepository = inviteRepository;
    }



    // 채팅방 생성
    public ChatRoom createChatRoom(String name, String username){
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        ChatRoom room = chatRoomRepository.save(ChatRoom.createRoom(name));
        FindRoom findRoom = new FindRoom();
        findRoom.setEmployee(employee);
        findRoom.setChatRoom(room);
        findRoomRepository.save(findRoom);
        return room;
    }



    // 나의 채팅방 조회
    public List<ChatRoom> getMyChatRooms(String username){
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        List<FindRoom> findRooms = findRoomRepository.findByEmployee(employee).orElseThrow(IllegalAccessError::new);
        List<ChatRoom> list = new ArrayList<>();
        findRooms.forEach(e -> {
            list.add(e.getChatRoom());
        });
        return list;
    }

    // 채팅방의 사용자 조회
    public List<Employee> getEmployeeInRoom(String code){
        List<Employee> list = new ArrayList<>();
        ChatRoom chatRoom = chatRoomRepository.findByCode(code)
                .orElseThrow(RuntimeException::new);
        List<FindRoom> findRooms = findRoomRepository.findByChatRoom(chatRoom)
                .orElseThrow(RuntimeException::new);
        findRooms.forEach(e -> {
            list.add(e.getEmployee());
        });

        return list;
    }

    // 채팅 방삭제
    public void deleteChatRoom(String code){
        ChatRoom chatRoom = chatRoomRepository.findByCode(code)
                .orElseThrow(RuntimeException::new);
        chatRoomRepository.delete(chatRoom);

    }

    // 채팅방 초대
    public InviteCard inviteEmployee(InviteDTO inviteDTO){
        Employee fromUser = employeeRepository.findEmployeeByUsername(inviteDTO.getFrom());
        Employee toUser = employeeRepository.findEmployeeByUsername(inviteDTO.getTo());
        Invite invite = Invite.builder()
                .code(inviteDTO.getCode())
                .from(fromUser)
                .to(toUser)
                .build();
        inviteRepository.save(invite);
        return new InviteCard(inviteDTO.getFrom(), inviteDTO.getCode());
    }

    // 초대장 목록
    public List<Invite> getInviteList(Channel channel){
        Employee employee = employeeRepository.findEmployeeByUsername(channel.getUsername());
        return inviteRepository.findByTo(employee)
                .orElseThrow(() -> new NoSuchDataException("해당데이터가 존재하지 않습니다."));    }
    // 채팅방 퇴장
    @Transactional
    public void exitChannel(String code, String username){
        ChatRoom chatRoom = chatRoomRepository
                .findByCode(code).orElseThrow(() -> (new IllegalArgumentException("채널이 존재하지 않습니다.")));

        // 제거할 유저
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        FindRoom findRoom = findRoomRepository.findByChatRoomAndEmployee(chatRoom, employee)
                .orElseThrow(RuntimeException::new);
        findRoomRepository.delete(findRoom);
    }

    public void deleteRoom(ChatRoom chatRoom){
        chatRoomRepository.delete(chatRoom);
    }
}
