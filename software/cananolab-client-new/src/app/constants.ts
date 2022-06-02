export const Consts = {

    LOGIN_URL: 'caNanoLab/login',
    QUERY_LOGOUT: 'caNanoLab/logout',
    waitTime: 11,

    // For each rest service
    QUERY_GET_TABS: 'caNanoLab/rest/core/getTabs',

    QUERY_SEARCH: 'caNanoLab/rest/customsearch/search',
    QUERY_INIT_SETUP: 'caNanoLab/rest/core/initSetup',
    QUERY_GET_USER_GROUPS: 'caNanoLab/rest/security/getUserGroups',
    QUERY_GET_COLLABORATION_GROUPS: 'caNanoLab/rest/core/getCollaborationGroup',
    QUERY_GET_USERS: 'caNanoLab/rest/core/getUsers',
    SAVE_COMPOSING_ELEMENT: 'caNanoLab/rest/nanomaterialEntity/saveComposingElement',

    QUERY_SAMPLE_SEARCH_BY_PUB: 'caNanoLab/rest/publication/searchById',
    QUERY_SAMPLE_GET_NAMES: 'caNanoLab/rest/sample/getSampleNames',
    QUERY_SAMPLE_COPY: 'caNanoLab/rest/sample/copySample',
    QUERY_SAMPLE_SUBMIT: 'caNanoLab/rest/sample/submitSample',
    QUERY_SAMPLE_SETUP: 'caNanoLab/rest/sample/setup',
    QUERY_SAMPLE_DELETE_FROM_WORKSPACE: 'caNanoLab/rest/sample/deleteSampleFromWorkspace',
    QUERY_SAMPLE_ADVANCED_SEARCH_SETUP: 'caNanoLab/rest/sample/setupAdvancedSearch',
    QUERY_SAMPLE_ADVANCED_SEARCH: 'caNanoLab/rest/sample/searchSampleAdvanced',
    QUERY_SEARCH_SAMPLE: 'caNanoLab/rest/sample/searchSample', // @TODO rename this SAMPLE_SEARCH
    QUERY_SAMPLE_AVAILABILITY: 'caNanoLab/rest/sample/viewDataAvailability',
    QUERY_SAMPLE_UPDATE: 'caNanoLab/rest/sample/updateSample',
    QUERY_SAMPLE_AVAILABILITY_HTML: 'caNanoLab/views/sample/view/sampleDataAvailability.html',  // @FIXME That's not how this works
    QUERY_SAMPLE_POC_UPDATE_SAVE:  'caNanoLab/rest/sample/savePOC',
    QUERY_SAMPLE_GET_CHARACTERIZATION_OPTIONS:  'caNanoLab/rest/sample/getDecoratedCharacterizationOptions',
    QUERY_SAMPLE_GET_DATUM_OPTIONS:  'caNanoLab/rest/sample/getDecoratedDatumOptions',
    QUERY_SAMPLE_GET_DATUM_UNIT_OPTIONS:  'caNanoLab/rest/sample/getDatumUnitOptions',

    QUERY_NANOMATERIAL_UPDATE: 'caNanoLab/rest/nanomaterialEntity/submit',
    QUERY_NANOMATERIAL_EDIT: 'caNanoLab/rest/nanomaterialEntity/edit',
    QUERY_NANOMATERIAL_SETUP: 'caNanoLab/rest/nanomaterialEntity/setup',
    QUERY_NANOMATERIAL_DELETE: 'caNanoLab/rest/nanomaterialEntity/delete',

    QUERY_PROTOCOL_SETUP: 'caNanoLab/rest/protocol/setup',
    QUERY_PROTOCOL_SAVE_ACCESS: 'caNanoLab/rest/protocol/saveAccess',
    QUERY_SEARCH_PROTOCOL: 'caNanoLab/rest/protocol/searchProtocol',
    QUERY_DELETE_PROTOCOL: 'caNanoLab/rest/protocol/deleteProtocol',
    QUERY_DELETE_PROTOCOL_BY_ID: 'caNanoLab/rest/protocol/deleteProtocol',
    QUERY_DELETE_PUBLICATION_BY_ID: 'caNanoLab/rest/publication/deletePublicationById',

    QUERY_CREATE_PROTOCOL: 'caNanoLab/rest/protocol/submitProtocol',
    QUERY_UPDATE_PROTOCOL: 'caNanoLab/rest/protocol/submitProtocol', // SAME AS create
    QUERY_EDIT_PROTOCOL: 'caNanoLab/rest/protocol/edit',
    QUERY_GET_PROTOCOL: 'caNanoLab/rest/protocol/getProtocol',
    QUERY_PROTOCOL_DELETE_ACCESS: 'caNanoLab/rest/protocol/deleteAccess',
    QUERY_DOWNLOAD_FILE: 'caNanoLab/rest/protocol/download',

    QUERY_UPLOAD_FILE: 'caNanoLab/rest/core/uploadFile',

    QUERY_GET_CHARACTERIZATION_BY_TYPE: 'caNanoLab/rest/sample/getCharacterizationByType',

    /*

    QUERY_GET_TABS: 'rest/core/getTabs',

    QUERY_SEARCH: 'rest/customsearch/search',
    QUERY_INIT_SETUP: 'rest/core/initSetup',
    QUERY_GET_USER_GROUPS: 'rest/security/getUserGroups',
    QUERY_GET_COLLABORATION_GROUPS: 'rest/core/getCollaborationGroup',
    QUERY_GET_USERS: 'rest/core/getUsers',
    QUERY_SAMPLE_SEARCH_BY_PUB: 'rest/publication/searchById',

    QUERY_PROTOCOL_SETUP: 'rest/protocol/setup',
    QUERY_SEARCH_PROTOCOL: 'rest/protocol/searchProtocol',
    QUERY_CREATE_PROTOCOL: 'rest/protocol/submitProtocol',

    QUERY_EDIT_PROTOCOL: 'rest/protocol/edit',
*/


    QUERY_ADD_FAVORITE: 'caNanoLab/rest/core/addFavorite',
    QUERY_GET_FAVORITE: 'caNanoLab/rest/core/getFavorites',
    QUERY_GET_WORKSPACE: 'caNanoLab/rest/core/getWorkspaceItems',
    QUERY_DELETE_FAVORITE: 'caNanoLab/rest/core/deleteFavorite',
    HELP_URL_FAVORITE: 'https://wiki.nci.nih.gov/x/b4AnEQ',
    HELP_URL_WORKSPACE: 'https://wiki.nci.nih.gov/x/fYQfEQ',
    HELP_URL_WORKFLOW: 'https://wiki.nci.nih.gov/display/caNanoLab/Getting+Started+in+caNanoLab#GettingStartedincaNanoLab-UsingtheWorkflowtoGetStarted',
    // Publications top menu
    HELP_URL_PUBLICATIONS: 'https://wiki.nci.nih.gov/x/e4QfEQ',
    // Publications within sample
    HELP_URL_SAMPLE_PUBLICATIONS: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Sample+Composition+Annotations#ManagingSampleCompositionAnnotations-Composition',
    HELP_URL_SAMPLE_SEARCH_BY_PUBLICATIONS: 'caNanoLab/rest/publication/searchById',
    HELP_URL_SAMPLE_CHARACTERIZATION: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Sample+Characterizations#ManagingSampleCharacterizations-Characterization',
    HELP_URL_SAMPLE_COMPOSITION: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Sample+Characterizations#ManagingSampleCharacterizations-Composition',
    HELP_URL_SAMPLE_COMPOSITION_NANOMATERIAL: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Sample+Composition+Annotations#ManagingSampleCompositionAnnotations-NanomaterialAddingNanomaterialEntityCompositionAnnotations',
    HELP_URL_SAMPLE_SEARCH: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Sample+Characterizations#ManagingSampleCharacterizations-Characterization',
    HELP_URL_SAMPLE_COPY: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Samples+in+caNanoLab#ManagingSamplesincaNanoLab-CopySample',

    HELP_URL_SAMPLE_CREATE: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Samples+in+caNanoLab#ManagingSamplesincaNanoLab-EditSample',
    HELP_URL_SAMPLE_EDIT: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Samples+in+caNanoLab#ManagingSamplesincaNanoLab-EditSample',
    HELP_URL_SAMPLE_VIEW: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Samples+in+caNanoLab#ManagingSamplesincaNanoLab-EditSample',
    HELP_URL_SAMPLE_ADVANCED_SEARCH: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Samples+in+caNanoLab#ManagingSamplesincaNanoLab-AdvSearchSample',

    HELP_URL_PROTOCOL_SEARCH: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Protocols+in+caNanoLab#ManagingProtocolsincaNanoLab-SearchProtocols',
    HELP_URL_PROTOCOL_MANAGE: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Protocols+in+caNanoLab',
    HELP_URL_PROTOCOL_CREATE: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Protocols+in+caNanoLab#ManagingProtocolsincaNanoLab-SubmitProtocol',
    HELP_URL_PROTOCOL_EDIT: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Protocols+in+caNanoLab#ManagingProtocolsincaNanoLab-EditProtocol',
    HELP_URL_PROTOCOL_SEARCH_RESULTS: 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Protocols+in+caNanoLab#ManagingProtocolsincaNanoLab-ProtocolResults',
}


export enum SortState{
    NO_SORT,
    SORT_UP,
    SORT_DOWN
}

export enum ProtocolScreen{
    PROTOCOL_SEARCH_RESULTS_SCREEN,
    PROTOCOL_SEARCH_INPUT_SCREEN,
    PROTOCOL_EDIT_SCREEN,
    PROTOCOL_VIEW_SCREEN,
}

export enum SampleScreen{
    SAMPLE_SEARCH_RESULTS_SCREEN,
    SAMPLE_SEARCH_INPUT_SCREEN,
    SAMPLE_MANAGE_SCREEN,
    SAMPLE_EDIT_SCREEN,
    SAMPLE_VIEW_SCREEN,
}
