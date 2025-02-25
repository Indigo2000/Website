import * as React from 'react';

const welcome = {
  heading: 'Hacker News Search Tool',
};

const API_ENDPOINT = 'https://hn.algolia.com/api/v1/search?query=';

const storiesReducer = (state, action) => {
  switch (action.type) {
    case 'STORIES_FETCH_INIT':
      return {
        ...state,
        isLoading: true,
        isError: false,
      }
    case 'STORIES_FETCH_SUCCESS':
      return {
        ...state,
        isLoading: false,
        isError: false,
        data: action.payload,
      };     
    case 'STORIES_FETCH_FAILURE':
      return {
        ...state,
        isLoading: false,
        isError: true,
      };

    case 'REMOVE_STORY':
      return {
        ...state,
        data: state.data.filter(
          (story) => action.payload.objectID !== story.objectID
        ),
      };
    default:
      throw new Error()
  }
  if (action.type === 'SET_STORIES') {
    
  } else {
    throw new Error ();
  }
};

const useStorageState = (key, initialState) => {
  const [value, setValue] = React.useState(
    localStorage.getItem(key) || initialState
      );

  React.useEffect(() => {
    localStorage.setItem(key, value);
  }, [value, key]);
  return [value, setValue]
};


const App = () => {
  
  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');
  const [stories, dispatchStories] = React.useReducer(
    storiesReducer ,
     { data: [], isLoading: false, isError: false }

  );
 

  React.useEffect(() => {
    if (!searchTerm) return;

    dispatchStories({ type: 'STORIES_FETCH_INIT'});

    fetch(`${API_ENDPOINT}${searchTerm}`)
      .then((response) => response.json())
      .then(result => {
        dispatchStories({
          type: 'STORIES_FETCH_SUCCESS',
          payload: result.hits,
      });
    })
    .catch(() => 
      dispatchStories({ type: 'STORIES_FETCH_FAILURE' })  
    );
  }, [searchTerm]);

  const handleRemoveStory = (item) => {
    dispatchStories({
      type: 'REMOVE_STORY',
      payload: item,
    },);
  };

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);

  };

  const searchedStories = stories.data.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      <h1>
        {welcome.heading}</h1>

        <InputWithLabel
        id="search"
        value={searchTerm}
        isFocused
        onInputChange={handleSearch} 
        >
          <strong>Search:</strong>
      </InputWithLabel>
      <hr />

      {stories.isError && <p>Something went wrong...</p>}

      {stories.isLoading ? (
        <p>Loading...</p>
      ) : (

      <List list={stories.data} onRemoveItem={handleRemoveStory}/>
      )}
    </div>
  );
};

const List = ({ list, onRemoveItem }) => (
    <ul>
      {list.map((item) => (
          <Item 
          key={item.objectID}
          item={item}
          onRemoveItem = {onRemoveItem}
           />
      )
    )}
  </ul>
  );

const Item = ({item, onRemoveItem}) => {
  const handleRemoveItem = () => {
    onRemoveItem(item);
  };
  
  return (
  <li>
    <span>
      <a href={item.url}> {item.title}</a>
    </span>
    <span> {item.author} </span>
    <span>{item.num_comments} </span>
    <span>{item.points} </span>
    <span>
      <button type="button" onClick={() => onRemoveItem(item)}>
        Dismiss
      </button>
    </span>
  </li>
);
};

const InputWithLabel =({id, label, value, type = 'text', onInputChange, isFocused, children}) => {

  const inputRef = React.useRef();

  React.useEffect(() => {
    if (isFocused && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isFocused]);  
  
  return (
    <>
      <label htmlFor={id}>{children}{label} </label>
      &nbsp;
      <input
        ref={inputRef} 
        id={id}
        type={type}
        value={value}
        onChange={onInputChange}
        />
    </>
  );
}
export default App
