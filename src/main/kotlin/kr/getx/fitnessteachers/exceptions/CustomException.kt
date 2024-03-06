package kr.getx.fitnessteachers.exceptions



class UserNotFoundException(val userId: Int) :
    RuntimeException("User not found with ID: $userId")

class UserNotFoundExceptionByEmail(val email: String) :
    RuntimeException("User not found with email: $email")

class ResumeNotFoundException(val resumeId: Int) :
    RuntimeException("Resume not found with ID: $resumeId")

class CenterNotFoundException(val centerId:Int) :
    RuntimeException("Center not found with ID: $centerId")
class CenterIdNotFoundExceptionByJobPost(message: String) :
    RuntimeException(message)
class CenterOwnershipException(val userId: Int, val centerId: Int) :
    RuntimeException("User $userId is not the owner of center $centerId")


class AuthenticationEmailNotFoundException :
    RuntimeException("Authentication information dose not contain an email address.")

class InvalidResumeOperationException(message: String) :
    RuntimeException(message)

class UserLoginFailedException(message: String) :
    RuntimeException(message)

class UserEditFailedException(message: String) :
    RuntimeException(message)

class JobPostNotFoundException(val jobPostId: Int) :
    RuntimeException("JobPost not found with ID: $jobPostId")