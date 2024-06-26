import org.apereo.cas.authentication.Credential
import org.apereo.cas.pm.*
import groovy.sql.Sql
def change(Object[] args) {
    def passwordChangeBean = args[0] as PasswordChangeRequest
    def logger = args[1]
    def url = 'jdbc:postgresql://172.17.9.95:35432/cas'
    def user = 'postgres'
    def password = 'abc123Aa'

    def sql = Sql.newInstance(url, user, password, 'org.postgresql.Driver')

     sql.execute("UPDATE users SET pwd = ? WHERE username = ?", [passwordChangeBean.toConfirmedPassword(), passwordChangeBean.username])

    sql.close()
    switch (passwordChangeBean.username) {
        case "bad-credential":
            return false
        case "error-credential":
            throw new InvalidPasswordException()
    }
    return true
}

def findEmail(Object[] args) {
    def username = (args[0] as PasswordManagementQuery).username
    def logger = args[1]
    if (username.equals("none")) {
        return null
    }
    return "cas@example.org"
}

def getSecurityQuestions(Object[] args) {
    def username = (args[0] as PasswordManagementQuery).username
    def logger = args[1]
    if (username.equals("noquestions")) {
        return [:]
    }
    return [securityQuestion1: "securityAnswer1"]
}

def findPhone(Object[] args) {
    def username = (args[0] as PasswordManagementQuery).username
    def logger = args[1]
    if (username.equals("none")) {
        return null
    }
    return "3477463421"
}

def findUsername(Object[] args) {
    def email = (args[0] as PasswordManagementQuery).email
    def logger = args[1]
    if (email.contains("@baddomain")) {
        return null
    }
    return "casuser"
}

def updateSecurityQuestions(Object[] args) {
    def query = args[0] as PasswordManagementQuery
    def logger = args[1]
    // Execute update...
}

def unlockAccount(Object[] args) {
    def query = args[0] as Credential
    def logger = args[1]
    // Execute unlock...
    return true
}
