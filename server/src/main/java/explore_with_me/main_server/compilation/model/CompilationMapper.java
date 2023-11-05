package explore_with_me.main_server.compilation.model;

/**
 * Compilation mapper
 */

public class CompilationMapper {

    /**
     * Compilation DTO to compilation.
     * @param compilationDto the compilation dto
     * @return the compilation
     */

    public static Compilation toCompilation(CompilationDto compilationDto) {
        Compilation compilation = new Compilation();
        compilation.setTitle(compilationDto.getTitle());
        compilation.setPinned(compilationDto.getPinned() != null ? compilationDto.getPinned() : false);

        return compilation;
    }
}
