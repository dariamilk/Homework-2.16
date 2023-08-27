package ru.hogwarts.school.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.exceptions.NoAvatarException;
import ru.hogwarts.school.exceptions.NoSuchStudentException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private Path avatarPath;

    @Value("${server.port}")
    private String port;

    @Value("${server.address}")
    private String host;
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService (AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public Avatar findById (Long id) {
        return avatarRepository.findById(id).orElseThrow(NoAvatarException::new);
    }

    public Long addAvatar (Long id, MultipartFile multipartFile) throws IOException {
        Student student = studentRepository.findById(id).orElseThrow(NoSuchStudentException::new);
        Path filePath = Path.of(avatarPath.toString(), student + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = multipartFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
                ) {
            bis.transferTo(bos);
        }
        if (avatarRepository.findAvatarByStudentId(id).isEmpty()) {
            Avatar avatar = new Avatar();
            avatar.setFilePath(filePath.toString());
            avatar.setStudent(student);
            avatar.setFileSize(multipartFile.getSize());
            avatar.setMediaType(multipartFile.getContentType());
            avatar.setData(multipartFile.getBytes());
            avatarRepository.save(avatar);
            return avatar.getId();
        } else {
            Avatar avatar = avatarRepository.findAvatarByStudentId(id).get();
            avatar.setFilePath(filePath.toString());
            avatar.setFileSize(multipartFile.getSize());
            avatar.setMediaType(multipartFile.getContentType());
            avatar.setData(multipartFile.getBytes());
            avatarRepository.save(avatar);
            return avatar.getId();
        }
    }

    public List<AvatarDTO> getPage (int num) {
        return avatarRepository.findAll(PageRequest.of(num, 1)).getContent().stream().map(avatar -> {
            try {
                return new AvatarDTO(avatar.getId(),
                        UriComponentsBuilder.newInstance()
                                .scheme("http")
                                .host(host)
                                .port(port)
                                .path("/avatar/" + avatar.getId() + "/avatar-from-db")
                                .build()
                                .toUri()
                                .toURL(),
                        avatar.getStudent().getId(), avatar.getStudent().getName());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }).collect(Collectors.toList());
    }

}
