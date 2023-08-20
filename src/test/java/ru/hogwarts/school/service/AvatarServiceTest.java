package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import java.util.List;
@ExtendWith(MockitoExtension.class)
class AvatarServiceTest {

    @Mock
    AvatarRepository avatarRepository;

    @InjectMocks
    AvatarService avatarService;
    @Test
    void getPage() {
        ReflectionTestUtils.setField(avatarService, "host", "localhost");
        ReflectionTestUtils.setField(avatarService, "port", "8085");
        Avatar avatar = new Avatar();
        avatar.setId(1L);
        avatar.setStudent(new Student(2L, "name", 22, null));
        Page<Avatar> page = new PageImpl<>(List.of(avatar), Pageable.ofSize(1), 1);
        Mockito.when(avatarRepository.findAll(PageRequest.of(1, 1))).thenReturn(page);
        Assertions.assertEquals(Long.valueOf(1L), avatarService.getPage(1).stream().mapToLong(AvatarDTO::getId).findFirst().getAsLong());
        Assertions.assertEquals(Long.valueOf(2L), avatarService.getPage(1).stream().mapToLong(AvatarDTO::getStudentId).findFirst().getAsLong());
        Assertions.assertEquals("name", avatarService.getPage(1).stream().map(AvatarDTO::getStudentName).findFirst().get());
        Assertions.assertEquals("http://localhost:8085/avatar/1/avatar-from-db", avatarService.getPage(1).stream().map(avatarDTO -> avatarDTO.getAvatarLink().toString()).findFirst().get());
    }
}