package com.AIBaraka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FileInfoService {

    @Autowired
    private FileInfoRepository fileInfoRepository;

    public FileInfo saveFileInfo(FileInfo fileInfo) {
        return fileInfoRepository.save(fileInfo);
    }

    public List<FileInfo> getFilesByUser(Integer userId) {
        return fileInfoRepository.findByUser_UserId(userId);
    }

    public List<FileInfo> getAllFiles() {
        return fileInfoRepository.findAll();
    }

    public Map<FileInfo.Status, Long> getFileStatusCounts() {
        List<Object[]> results = fileInfoRepository.countFilesByStatusGrouped();  // This method returns the grouped counts
        return results.stream()
            .collect(Collectors.toMap(
                row -> (FileInfo.Status) row[0],  // The status is in the first element of the array
                row -> (Long) row[1]              // The count is in the second element of the array
            ));
    }

    public FileInfo updateFileStatus(Integer fileId, FileInfo.Status status, String comments) {
        FileInfo fileInfo = fileInfoRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
        fileInfo.setStatus(status);
        fileInfo.setComments(comments);
        return fileInfoRepository.save(fileInfo);
    }


    public FileInfo getFileById(Integer fileId) {
        return fileInfoRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }
}
