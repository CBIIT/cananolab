export let Properties = {

    // Be sure API_SERVER_URL is an empty string for non development environments (no ng start)
    API_SERVER_URL: '',
    // API_SERVER_URL: 'http://192.168.1.25:4600',

    MAX_PAGER_BUTTONS: 6,
    DEFAULT_PAGE_LENGTH: 10,
    MAX_PAGE_LENGTH: 200,

    DISCLAIMER_URL: 'html/cananoDisclaimer.html',

    // Show in console, curl version of post & get calls.
    DEBUG_CURL: true,

    // This value can be changed in the config file.
    HTTP_TIMEOUT: 120000, // Thousands of a second

    MAX_WIDTH: '1600px',  // @TODO make this a configuration file thing

    CONFIG_FILE: 'configuration',
    CONFIG_COMPLETE: false, // @TODO Do not forget to implement checking for & updating this value

    // Don't access the server, only use test data.
    TEST_MODE: false,

    current_user: 'xx',
    logged_in: false,
    LOGGED_IN: false,

    COUNTER: 0,

    PROTOCOL_TYPES: [],
    PROTOCOL_CSM_ROLES: [],
    SAMPLE_TOOLS: false,
    CURRENT_SAMPLE_ID: 0,
    CURRENT_SAMPLE_NAME: '',
    GROUPS:[]

}
