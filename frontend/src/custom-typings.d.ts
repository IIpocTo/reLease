// support NodeJS modules without type definitions
declare module "*";

// Extra variables that live on Global that will be replaced by webpack DefinePlugin
declare var ENV: string;
declare var System: SystemJS;

interface SystemJS {
    import: (path?: string) => Promise<any>;
}

interface GlobalEnvironment {
    ENV: string;
    SystemJS: SystemJS;
    System: SystemJS;
}

interface Global extends GlobalEnvironment  {}
