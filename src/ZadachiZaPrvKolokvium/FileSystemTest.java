package src.ZadachiZaPrvKolokvium;

import java.util.*;
import java.util.stream.Collectors;

public class FileSystemTest {

    public static Folder readFolder (Scanner sc)  {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i=0;i<totalFiles;i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String [] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args)  {

        //file reading from input

        Scanner sc = new Scanner (System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());


    }
}

interface IFile extends Comparable<IFile>{
    String getFileName();
    long getFileSize();
    String getFileInfo(int indent);
    void sortBySize();
    long findLargestFile();
}

class File implements IFile{

    protected String name;
    protected long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public File(String fileName) {
        this.name = fileName;
        this.size = 0L;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<indent;i++){
            builder.append("\t");
        }
        String pom = String.format("File name: %10s File size: %10d\n",this.name,this.size);
        builder.append(pom);
        return builder.toString();
    }

    @Override
    public void sortBySize() {
        return;
    }

    @Override
    public long findLargestFile() {
        return this.size;
    }

    @Override
    public int compareTo(IFile other) {
        return Long.compare(this.getFileSize(),other.getFileSize());
    }
}

class Folder extends File implements IFile{

    private List<IFile> fileList;

    public Folder(String fileName) {
        super(fileName);
        this.fileList = new ArrayList<>();
    }

    void addFile(IFile file) throws FileNameExistsException {
        boolean isPresent = fileList.stream()
                                .map(IFile::getFileName)
                                .anyMatch(i -> i.equals(file.getFileName()));
        if(isPresent){
            throw new FileNameExistsException(file.getFileName(),this.name);
        }
        fileList.add(file);
    }

    @Override
    public String getFileName() {
        return this.name;
    }

    @Override
    public long getFileSize() {
        return this.fileList.stream().mapToLong(IFile::getFileSize).sum();
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<indent;i++){
            builder.append("\t");
        }
        String pom = String.format("Folder name: %10s Folder size: %10d\n",this.name,this.getFileSize());
        builder.append(pom);
        fileList.stream().forEach(file -> builder.append(file.getFileInfo(indent+1)));
        return builder.toString();
    }

    @Override
    public void sortBySize() {
        fileList.sort(Comparator.comparing(IFile::getFileSize));
        fileList.forEach(IFile::sortBySize);
    }

    @Override
    public long findLargestFile() {
        OptionalLong largest = fileList.stream()
                .mapToLong(IFile::findLargestFile)
                .max();
        return largest.isPresent()?largest.getAsLong():0L;
    }
}

class FileSystem{

    private Folder rootDirectory;

    public FileSystem() {
        rootDirectory = new Folder("root");
    }

    void addFile(IFile file) throws FileNameExistsException {
        rootDirectory.addFile(file);
    }

    long findLargestFile(){
        return rootDirectory.findLargestFile();
    }

    void sortBySize(){
        rootDirectory.sortBySize();
    }

    @Override
    public String toString() {
        return rootDirectory.getFileInfo(0);
    }
}

class FileNameExistsException extends Exception{
    private String fileName;
    private String folderName;

    public FileNameExistsException(String fileName, String folderName) {
        this.fileName = fileName;
        this.folderName = folderName;
    }

    public String getMessage(){
        return String.format("There is already a file named %s in the folder %s",fileName,folderName);
    }
}