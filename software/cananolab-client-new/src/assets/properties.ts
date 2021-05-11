export let Properties = {

    // Be sure API_SERVER_URL is an empty string for non development environments (no ng start)
    // API_SERVER_URL: '',
     API_SERVER_URL: 'http://192.168.1.25:4600/caNanoLab',

    // Show in console, curl version of post & get calls.
    DEBUG_CURL: true,

    // This value can be changed in the config file.
    HTTP_TIMEOUT: 120000, // Thousands of a second

    CONFIG_FILE: 'configuration',
    CONFIG_COMPLETE: false, // @TODO Do not forget to implement checking for & updating this value

    // Don't access the server, only use test data.
    TEST_MODE: false

}
