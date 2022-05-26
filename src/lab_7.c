#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <crypt.h>

int encryptPasswd(char* str) {
    char* passwdEncrypted = crypt(str, "none");
    printf("%s\n", passwdEncrypted);
    return 0;
}

int encryptPasswdToFile(FILE* fp, char* salt, char* password) {
    printf("%s$", salt);
    printf("%s\n", password);
    char* passwdEncrypted = crypt(password, salt);
    fprintf(fp, "%s\n", passwdEncrypted);
    return 0;
}

char* copyString(char s[])
{
    int i;
    char* s2;
    s2 = (char*)malloc(100);
 
    // Executing till null character
    // is found
    for (i = 0; s[i] != '\n'; i++) {
 
        // Copy the character one
        // by one from s1 to s2
        s2[i] = s[i];
    }
    s2[i] = '\0';
 
    // Return the pointer of newly
    // created string
    return (char*)s2;
}

//compile:
//gcc -o run lab_7.c -lcrypt
//./run -f genSalt.txt genPasswords.txt
int main(int argc, char *argv[]) {

    //if file was provided read it
    if (strcmp(argv[1], "-f") == 0) {
        printf("%s\n", "Reading File...");
        char *salts[100];
        char *passwords[100];

        FILE *fp = fopen(argv[2], "r"); // read mode
        // write mode
        char * line = NULL;
        size_t len = 0;
        int read;
        int i = 0;
        while ((read = getline(&line, &len, fp)) != -1) {
            char *sub = copyString(&line[0]);
            salts[i] = sub;
            i = i + 1;
        }
        fclose(fp);

        fp = fopen(argv[3], "r"); // read mode
        // write mode
        line = NULL;
        len = 0;
        i = 0;
        while ((read = getline(&line, &len, fp)) != -1) {
            char *sub = copyString(&line[0]);
            passwords[i] = sub;
            i = i + 1;
        }
        fclose(fp);


        FILE *fw = fopen("hashedPasswords.txt", "w"); 
        for (int t = 0; t < i; t = t + 1) {
            // char *salt = crypt_gensalt("$2b$", 15, NULL, 0);
            // fprintf(fs, "%s\n", salt);
            encryptPasswdToFile(fw, salts[t], passwords[t]);
        }
        fclose(fw);
    } else {
        encryptPasswd(argv[1]);
    }

    

    return 0;
}


