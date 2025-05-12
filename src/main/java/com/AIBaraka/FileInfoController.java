package com.AIBaraka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileInfoController {

	@Autowired
	private FileInfoService fileInfoService;

	@Autowired
	private UserRepository userRepository;

	public static class FileStatusUpdateRequest {
		private FileInfo.Status status;
		private String comments;

		public FileInfo.Status getStatus() {
			return status;
		}

		public void setStatus(FileInfo.Status status) {
			this.status = status;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}
	}

	@PostMapping("/upload")
	public FileInfo uploadFile(@RequestBody FileInfo fileInfo) {
		User user = userRepository.findById(fileInfo.getUser().getUserId())
			.orElseThrow(() -> new RuntimeException("User not found"));
		fileInfo.setUser(user);
		return fileInfoService.saveFileInfo(fileInfo);
	}

	@GetMapping("/user/{userId}")
	public List<FileInfo> getUserFiles(@PathVariable Integer userId) {
		return fileInfoService.getFilesByUser(userId);
	}

	@GetMapping("/admin/all")
	public List<FileInfo> getAllFiles() {
		return fileInfoService.getAllFiles();
	}

	@GetMapping("/admin/status-counts")
	public Map<FileInfo.Status, Long> getStatusCounts() {
		return fileInfoService.getFileStatusCounts();
	}

	@PatchMapping("/status/{fileId}")
	public FileInfo updateFileStatus(@PathVariable Integer fileId,
									 @RequestBody FileStatusUpdateRequest updateRequest) {
		return fileInfoService.updateFileStatus(fileId, updateRequest.getStatus(), updateRequest.getComments());
	}

	@PatchMapping("/approve/{fileId}")
	public FileInfo approveFile(@PathVariable Integer fileId,
								@RequestBody FileStatusUpdateRequest updateRequest) {
		return fileInfoService.updateFileStatus(fileId, FileInfo.Status.APPROVED, updateRequest.getComments());
	}

	@PatchMapping("/reject/{fileId}")
	public FileInfo rejectFile(@PathVariable Integer fileId,
							   @RequestBody FileStatusUpdateRequest updateRequest) {
		return fileInfoService.updateFileStatus(fileId, FileInfo.Status.REJECTED, updateRequest.getComments());
	}

	@GetMapping("/{fileId}")
	public FileInfo getFileById(@PathVariable Integer fileId) {
		return fileInfoService.getFileById(fileId);
	}
}
