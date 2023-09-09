package ru.practicum.main_server.compilation.model;

public class CompilationMapper {

    public static Compilation toCompilation(CompilationDto compilationDto) {
        Compilation compilation = new Compilation();
        compilation.setTitle(compilationDto.getTitle());
        compilation.setPinned(compilationDto.getPinned() != null ? compilationDto.getPinned() : Boolean.valueOf(false));

        return compilation;
    }
}
