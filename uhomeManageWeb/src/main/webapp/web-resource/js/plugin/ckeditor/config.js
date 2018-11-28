/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	config.width = '700';
	
	config.toolbar = 'MyToolbar';
    config.toolbar_MyToolbar =
    [  
        ['Source','-','NewPage','Preview','Print','-','Templates'],  
        ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo'],  
        ['Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt'],  
        ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],  
        '/',  
        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],  
        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],  
        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],  
        ['Link','Unlink','Anchor'],  
        ['Image','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
        '/',  
        ['Styles','Format','Font','FontSize'],  
        ['TextColor','BGColor'],  
        ['Maximize', 'ShowBlocks']
    ];
   
};
